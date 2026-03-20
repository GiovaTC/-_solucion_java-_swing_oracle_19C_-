import java.sql.Connection;
import java.sql.DriverManager;

public class ConexionOracle {

    private static final String URL = "jdbc:oracle:thin://@localhost:1521/orcl";
    private static final String USER = "system";
    private static final String PASS = "Tapiero123";

    public static Connection getConexion() throws Exception {
        Class.forName("oracle.jdbc.driver.OracleDriver");
        return DriverManager.getConnection(URL,USER,PASS);
    }
}
