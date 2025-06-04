package ViewRouteHelper;

public class ViewRouteHelper {
	
		//HOME
		public final static String INDEX = "home/index";
		
		//ADMIN
		public final static String ADMIN_INDEX = "admin/index";
		public final static String ADMIN_CREATE = "admin/create";
		public final static String ADMIN_EDIT = "admin/edit";
		public final static String REDIRECT_ADMIN_USERS_CRUD = "redirect:/admin";
		
		//ASSISTANCE_RESPONSE
		public final static String ASSISTANCE_RESPONSE_INDEX = "assistance_response/index";
		public final static String REDIRECT_ASSISTANCE_RESPONSE_INDEX = "redirect:/responses/list/";
		
		//USER
		public final static String USER_LOGIN = "access/login";
		
		//EVENT
		public final static String TICKET_INDEX = "ticket/index";
		public final static String TICKET_SAVE = "ticket/save";
		public final static String TICKET_CREAR = "ticket/crear";
		public final static String REDIRECT_EVENTS_CRUD = "redirect:/ticket";
		
		//REGISTRO
		public final static String REGISTRO_EXITO = "registro/exito";
		public final static String REGISTRO_INDEX = "registro/index";
		public final static String REGISTRO_RESUME = "registro/resumen";
		public final static String REGISTRO_NOTFOUND = "registro/notfound";
		public final static String REGISTRO_ERROR = "registro/error";
		public final static String REGISTRO_MAIL_IN_USE = "registro/mail_in_use";
		
		//QR
		public final static String QR_RESULT = "qr/result";
		public final static String QR_ASSISTANCE = "qr/assitance";
		
		//UPLOADS
		public final static String UPLOADS_IMAGES_EVENTS = "uploads/images/events";
}
