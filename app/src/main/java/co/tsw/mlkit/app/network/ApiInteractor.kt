package co.tsw.mlkit.app.network

interface ApiInteractor {

    fun onStartApi()

    fun onNextApi(api: String, jsonObject: Any?)

    fun onErrorApi(api: String, e: Any)

    fun onFinishApi()
}