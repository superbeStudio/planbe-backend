package account.superbe.common.client

interface TokenClient {
    fun setValues(token: String, id: String)
    fun getValues(token: String): String
    fun delValues(token: String)
}