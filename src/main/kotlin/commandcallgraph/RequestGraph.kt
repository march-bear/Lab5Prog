package commandcallgraph

import Organization
import collection.CollectionWrapper
import iostreamers.Messenger
import requests.Request
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class RequestGraph(private val collection: CollectionWrapper<Organization>) {
    private val leafs: ArrayList<Leaf> = ArrayList()
    private var currLeaf: Leaf

    init {
        leafs.add(Leaf(null, "ROOT", null))
        currLeaf = leafs.first()
    }

    fun addLeaf(request: Request, name: String = request::class.simpleName ?: "REQUEST"): String {
        val requestClassName = request::class.simpleName.toString().uppercase()
        val id = "${name.uppercase()}_${getNumber(requestClassName)}"

        val newLeaf = Leaf(request, id, currLeaf)
        currLeaf = newLeaf

        leafs.add(newLeaf)

        return id
    }

    fun rollback(leafId: String) {
        val targetLeaf = leafs.find { it.id == leafId}

        if (targetLeaf == null) {
            println("Запрос не найден")
            return
        } else {
            val route = getRoute(currLeaf, targetLeaf)
            while (route.isNotEmpty()) {
                val (action, request) = route.poll()
                when (action) {
                    RequestAction.PROCESSING -> request.process(collection)
                    RequestAction.CANCELLATION -> request.cancel()
                }
            }
        }

        currLeaf = targetLeaf
    }

    private fun getRoute(currLeaf: Leaf, targetLeaf: Leaf): Queue<Pair<RequestAction, Request>> {
        val result: Queue<Pair<RequestAction, Request>> = LinkedList()
        if (currLeaf == targetLeaf)
            return LinkedList()

        val routeFromRootToCurr = LinkedList<Leaf>()
        var previousToCurrLeaf = currLeaf

        while (previousToCurrLeaf.id != "ROOT") {
            routeFromRootToCurr.add(previousToCurrLeaf)
            previousToCurrLeaf = previousToCurrLeaf.previousLeaf!!

            if (previousToCurrLeaf == targetLeaf) {
                for (leaf in routeFromRootToCurr)
                    result.add(Pair(RequestAction.CANCELLATION, leaf.request!!))
                return result
            }
        }

        val routeFromCurrBranchToTarget = LinkedList<Leaf>()
        var previousToTargetLeaf = targetLeaf

        while (previousToTargetLeaf.id != "ROOT") {
            if (previousToTargetLeaf in routeFromRootToCurr) {
                var currInRouteRootToCurr: Leaf = routeFromRootToCurr[0]
                while (currInRouteRootToCurr != previousToTargetLeaf) {
                    result.add(Pair(RequestAction.CANCELLATION, currInRouteRootToCurr.request!!))
                    currInRouteRootToCurr = currInRouteRootToCurr.previousLeaf!!
                }

                for (leaf in routeFromCurrBranchToTarget.descendingIterator())
                    result.add(Pair(RequestAction.PROCESSING, leaf.request!!))

                return result
            }

            routeFromCurrBranchToTarget.add(previousToTargetLeaf)
            previousToTargetLeaf = previousToTargetLeaf.previousLeaf!!
        }
        for (leaf in routeFromRootToCurr)
            result.add(Pair(RequestAction.CANCELLATION, leaf.request!!))

        for (leaf in routeFromCurrBranchToTarget.descendingIterator())
            result.add(Pair(RequestAction.PROCESSING, leaf.request!!))

        return result
    }

    private companion object {
        val countNumbers: HashMap<String, Long> = HashMap()
        fun getNumber(commandName: String): Long {
            if (countNumbers[commandName] == null)
                countNumbers[commandName] = 1L
            else
                countNumbers[commandName] = countNumbers[commandName]!! + 1L

            return countNumbers[commandName]!!
        }
    }
}

enum class RequestAction {
    CANCELLATION,
    PROCESSING,
}