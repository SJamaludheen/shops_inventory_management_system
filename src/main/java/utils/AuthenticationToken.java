package utils;

public class AuthenticationToken {
        private static String token;

        private AuthenticationToken() {

        }

        public static String getAuthenticationToken() {
                return token;
        }

        public static void setAuthenticationToken(String token) {
                AuthenticationToken.token = token;
        }
}
