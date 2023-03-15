package requests

import Organization
import collection.*

class ChangeCollectionTypeRequest(private val collectionType: CollectionType) : Request {
    override fun process(collection: CollectionWrapper<Organization>): Response {
        val wrapper = when (collectionType) {
            CollectionType.SET -> LinkedHashSetWrapper<Organization>()
            CollectionType.QUEUE -> ConcurrentLinkedQueue<Organization>()
            CollectionType.LIST -> LinkedListWrapper<Organization>()
        }

        collection.replaceCollectionWrapper(wrapper)

        return Response(true, "Good")
    }

    override fun cancel(): String {
        return "Ага"
    }
}