import android.util.Log
import com.example.fastfoodmanagement.Model.Product
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class ProductService {
    private val db = FirebaseFirestore.getInstance()
    private val productCollection = db.collection("products")

    suspend fun addProduct(product: Product): Boolean {
        return try {
            productCollection.add(product).await()
            true
        } catch (e: Exception) {
            Log.e("ProductService", "Error al agregar producto: ${e.message}")
            false
        }
    }

    suspend fun deleteProduct(productId: String): Boolean {
        return try {
            productCollection.document(productId).delete().await()
            true
        } catch (e: Exception) {
            Log.e("ProductService", "Error al eliminar producto: ${e.message}")
            false
        }
    }

    suspend fun getProducts(): List<Product> {
        return try {
            productCollection.get().await().toObjects(Product::class.java)
        } catch (e: Exception) {
            Log.e("ProductService", "Error al obtener productos: ${e.message}")
            emptyList()
        }
    }

    suspend fun deleteProductByName(productName: String): Boolean {
        return try {
            val querySnapshot = productCollection
                .whereEqualTo("name", productName)
                .get()
                .await()

            if (querySnapshot.documents.isNotEmpty()) {
                for (document in querySnapshot.documents) {
                    productCollection.document(document.id).delete().await()
                }
                true
            } else {
                false
            }
        } catch (e: Exception) {
            Log.e("ProductService", "Error al eliminar producto por nombre: ${e.message}")
            false
        }
    }
}
