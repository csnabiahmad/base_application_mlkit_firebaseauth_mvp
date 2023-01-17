package co.tsw.mlkit.app.common

object Constants {

    @JvmField
    var token: String = ""

    @JvmField
    var isLogin: Boolean = false

    //    const val BASE_URL = "http://sbk.tswtechnologies.sg:8069"
//    const val BASE_URL = "http://enterprise.tswtechnologies.sg:8069" //no logo
//    const val BASE_URL = "http://192.168.5.157:8069"
    const val BASE_URL = "http://47.241.67.245:8069"

    const val URL_COMMON = "%s/xmlrpc/2/common"
    const val URL_OBJECT = "%s/xmlrpc/2/object"

//    const val DATABASE = "odoo11"
//    const val DATABASE = "sbk3"
//    const val DATABASE = "enterprise"

//    const val DATABASE = "teeni"
    const val DATABASE = "teeni_prep"

    const val CONTROLLER_CALL_BUTTON = "/web/dataset/call_button"
    const val CONTROLLER_CALL = "/web/dataset/call_kw"


    /// REST API ///
    const val GET_DRIVERS = "/get_drivers"
    const val SET_DRIVERS = "/assign_driver_to_do"
    const val GET_WAREHOUSES = "/get_warehouse"
    const val POST_WAREHOUSES_OPERATIONS = "/get_picking_rec"
    const val POST_WAREHOUSES_OPERATIONS_GROUP = "/get_customer_picking_rec"
    const val POST_LOT_NUMBERS = "/get_lot_no"
    const val CREATE_BACK_ORDER = "/create_backorder"
    const val PSOT_USER_RIGHTS = "/get_user_rights"

    const val MODEL_RES_PARTNER = "res.partner"
    const val MODEL_RES_USERS = "res.users"
    const val MODEL_ACCOUNT_INVOICE = "account.invoice"
    const val MODEL_ACCOUNT_PAYMENT = "account.payment"
    const val MODEL_PRODUCT_TEMPLATE = "product.template"
    const val MODEL_PRODUCT_PRODUCT = "product.product"
    const val MODEL_ACCOUNT_INVOICE_LINE = "account.invoice.line"
    const val MODEL_ACCOUNT_TAX = "account.tax"
    const val MODEL_ACCOUNT_INVOICE_TAX = "account.invoice.tax"
    const val MODEL_PRODUCT_CATEGORY = "product.category"
    const val MODEL_STOCK_CHANGE_PRODUCT_QTY = "stock.change.product.qty"
    const val MODEL_STOCK_CHANGE_PRODUCT_QTY_CREATE = "$CONTROLLER_CALL/stock.change.product.qty/create"
    const val MODEL_SALE_ORDER = "sale.order"
    const val MODEL_SALE_ADVANCE_PAYMENT_INV = "sale.advance.payment.inv"
    const val MODEL_SALE_ORDER_LINE = "sale.order.line"
    const val MODEL_ACCOUNT_PAYMENT_TERM = "account.payment.term"
    const val MODEL_STOCK_PICKING_TYPE = "stock.picking.type"
    const val MODEL_STOCK_PICKING = "stock.picking"
    const val MODEL_ASSIGN_DRIVER = "assign.driver.wiz"
    const val MODEL_IR_ATTACHMENT = "ir.attachment"
    const val MODEL_MESSAGE_POST = "$CONTROLLER_CALL/sale.order/message_post"
    const val MODEL_MESSAGE_POST_DO = "$CONTROLLER_CALL/stock.picking/message_post"
    const val MODEL_MESSAGE_POST_ACCOUNTING_INVOICE = "$CONTROLLER_CALL/account.invoice/message_post"
    const val MODEL_PUSH_DEVICE = "$CONTROLLER_CALL/sale.push_device/create"
    const val MODEL_REMOVE_PUSH_DEVICE = "$CONTROLLER_CALL/sale.push_device/unlink"
    const val MODEL_STOCK_MOVE = "stock.move"
    const val MODEL_STOCK_MOVE_LINE = "stock.move.line"
    const val MODEL_STOCK_PRODUCTION_LOT = "stock.production.lot"
    const val MODEL_STOCK_BACKORDER_CONFIRM = "stock.backorder.confirmation"

    const val AUTHENTICATE = "authenticate"

    const val METHOD_ACTION_INVOICE_OPEN = "action_invoice_open"
    const val METHOD_ACTION_VALIDATE_INVOICE_PAYMENT = "action_validate_invoice_payment"
    const val METHOD_ACTION_PRINT_QUOTATION = "print_quotation"
    const val METHOD_ACTION_QUOTATION_SEND = "action_quotation_send"
    const val METHOD_ACTION_SALE_CONFIRM = "action_confirm"
    const val METHOD_ACTION_SALE_DONE = "action_done"
    const val METHOD_ACTION_SALE_UNLOCK = "action_unlock"
    const val METHOD_CREATE_INVOICES = "create_invoices"
    const val METHOD_READ = "read"
    const val METHOD_ACTION_INVOICE_SENT = "action_invoice_sent"
    const val METHOD_INVOICE_PRINT = "invoice_print"
    const val METHOD_BUTTON_VALIDATE = "button_validate"
    const val METHOD_ACTION_CANCEL = "action_cancel"
    const val METHOD_BUTTON_SUBMIT = "packer_submit"
    const val METHOD_BUTTON_VERIFY = "wha_verify"
    const val METHOD_BUTTON_PROCESS = "button_validate"
    const val METHOD_BUTTON_APPROVED = "manager_approve"
    const val METHOD_BUTTON_CONFIRM = "packer_confirm"
    const val METHOD_BUTTON_PACKITEM = "packer_pack"
    const val METHOD_BUTTON_PACK_VERIFY = "la_verify"
    const val METHOD_BUTTON_DRIVER_PICK = "driver_pick"
    const val METHOD_BUTTON_COMPLETE_DELIVERY = "deliver_confirm"
    const val METHOD_NAME_SEARCH = "name_search"
    const val METHOD_BACKORDER_PROCESS = "process"
    const val METHOD_BACKORDER_CANCEL = "process_cancel_backorder"

    const val PARTNER_ID = "partner_id"
    const val ID = "id"

    // API
    const val URL_DOWNLOAD_INVOICE = "/report/pdf/account.report_invoice/"
    const val URL_DOWNLOAD_SALES = "/report/pdf/sale.report_saleorder/"
    const val URL_DOWNLOAD_SALES_ORDER_PRO_FORMA = "/report/pdf/sale.report_saleorder_pro_forma/"
    const val URL_DOWNLOAD_STOCK_REPORT_DELIVERYSLIP = "/report/pdf/stock.report_deliveryslip/"
    const val UPLOAD_SIGNATURE_IMAGE = "/insert_sign"

    // API Parameter
    const val PARAM_COOKIE = "Cookie"
    const val PARAM_SESSION_ID = "session_id"

    // KEYs
    const val SHARED_PREFERENCES_KEY = "appex_shared_preference_key"
    const val SHARED_PREFERENCES_KEY_USERNAME = "appex_shared_preference_key_username"
    const val SHARED_PREFERENCES_KEY_USERID = "appex_shared_preference_key_user_id"
    const val SHARED_PREFERENCES_KEY_PASSWORD = "appex_shared_preference_key_password"
    const val SHARED_PREFERENCES_KEY_TIME = "appex_shared_preference_key_time"
    const val SHARED_PREFERENCES_KEY_SAVE_USER_PASSWORD = "appex_shared_preference_key_save_user_password"
    const val SHARED_PREFERENCES_KEY_TOKEN = "appex_shared_preference_key_token"

    const val KEY_USER_RIGHTS_PACKER = "packerRights"
    const val KEY_USER_RIGHTS_WH_ASSIST = "whAssistRights"
    const val KEY_USER_RIGHTS_MANAGER = "managerRights"
    const val KEY_USER_RIGHTS_DRIVER = "driverRights"
    const val KEY_USER_RIGHTS_LOGISTIC = "logisticAssistRights"
    const val KEY_USER_RIGHTS_ADMIN = "adminRights"
    const val KEY_CREATE_BACKORDER = "create_backorder"
    const val KEY_NO_BACKORDER = "no_backorder"

    //extra
    const val EXTRA_CUSTOMER = "appex_customer"
    const val EXTRA_INVOICE = "appex_invoice"
    const val EXTRA_SALESPERSON = "appex_salesperson"
    const val EXTRA_PRODUCT = "appex_product"
    const val EXTRA_IS_EDIT_CUSTOMER = "appex_is_edit_customer"

    //case screen position
    const val SCREEN_INVOICE = 0
    const val SCREEN_QUOTATIONS = 1
    const val SCREEN_ORDER = 2
    const val SCREEN_PRODUCT = 3
    const val SCREEN_CUSTOMER = 4
    const val SCREEN_INVENTORY = 5
    const val SCREEN_DELIVERY_ORDERS = 6


    //// module
    const val MODULE_RECEIPTS = "Receipts"
    const val MODULE_INTERNAL_TRANSFERS = "Internal Transfers"
    const val MODULE_PICK = "Pick"
    const val MODULE_DELIVERY_ORDERS = "Delivery Orders"

    /// status
    const val STATUS_DRAFT = "DRAFT"
    const val STATUS_WAITING_ANOTHER_OPERATION = "WAITING ANOTHER OPERATION"
    const val STATUS_WAITING = "WAITING"
    const val STATUS_VERIFIED = "VERIFIED"
    const val STATUS_READY = "READY"
    const val STATUS_REQUEST_SUBMITTED = "REQUEST SUBMITTED"
    const val STATUS_MANAGE_APPROVED = "MANAGE APPROVED"
    const val STATUS_WH_ASSIST_PROCESSED = "WH ASSIST PROCESSED"
    const val STATUS_ITEM_PACKED = "ITEM PACKED"
    const val STATUS_PACKING_VERIFIED = "PACKING VERIFIED"
    const val STATUS_OUT_FOR_DELIVERY = "OUT FOR DELIVERY"
    const val STATUS_DONE = "DONE"
    const val STATUS_CANCELLED = "CANCELLED"
    const val STATUS_REJECTED = "REJECTED"




    // Data format
    const val DATE_FORMAT_DEFAULT = "dd/MM/yyyy"
    const val DATE_FORMAT_YYYY_MM_DD = "yyyy-MM-dd"

}