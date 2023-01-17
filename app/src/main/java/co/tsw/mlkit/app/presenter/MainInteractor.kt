package co.tsw.mlkit.app.presenter

interface MainInteractor {

    fun onStartApi()

    fun onNextApi(api: String, jsonObject: Any?)

    fun onErrorApi(api: String, e: Any)

    fun onFinishApi()
}