import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import java.time.Duration;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.assertEquals;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DisplayName("Test de Cadastro")
public class RegisterTest {

    private static WebDriver driver;

    @BeforeAll
    public static void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }

    @BeforeEach
    public void loadingPattern() {
        driver.get("https://front.serverest.dev/login");
    }

    @AfterAll
    public static void tearDown() {
        System.out.println("Teste finalizado!");
        driver.quit();
    }

    //Gera email único
    private String generateUniqueEmail() {
        String uuid = UUID.randomUUID().toString().substring(0, 8);
        return "joao" + uuid + "@email.com";
    }

    @Test
    @Order(1)
    @DisplayName("Cadastro com sucesso")
    public void testSuccessfulRegistration() {
        String emailUnico = generateUniqueEmail();

        driver.manage().window().setSize(new Dimension(1366, 720));
        assertEquals("Cadastre-se", driver.findElement(By.linkText("Cadastre-se")).getText());
        driver.findElement(By.linkText("Cadastre-se")).click();
        driver.findElement(By.id("nome")).click();
        driver.findElement(By.id("nome")).sendKeys("João");
        driver.findElement(By.id("email")).click();
        driver.findElement(By.id("email")).sendKeys(emailUnico);
        driver.findElement(By.id("password")).click();
        driver.findElement(By.id("password")).sendKeys("123456");
        driver.findElement(By.id("administrador")).click();
        assertEquals("Cadastrar",driver.findElement(By.cssSelector(".btn-primary")).getText(),"Texto esperado 'Cadastrar', mas foi exibido outro texto");
        driver.findElement(By.cssSelector(".btn-primary")).click();
        assertEquals("Cadastro realizado com sucesso",driver.findElement(By.cssSelector("a.alert-link")).getText(),"Texto esperado 'Cadastro realizado com sucesso', mas foi exibido outro texto");
    }

    @Test
    @Order(2)
    @DisplayName("Cadastro com email duplicado")
    public void testDuplicateEmailRegistration() {

        driver.manage().window().setSize(new Dimension(1366, 720));
        assertEquals("Cadastre-se", driver.findElement(By.linkText("Cadastre-se")).getText());
        driver.findElement(By.linkText("Cadastre-se")).click();
        driver.findElement(By.id("nome")).click();
        driver.findElement(By.id("nome")).sendKeys("João");
        driver.findElement(By.id("email")).click();
        driver.findElement(By.id("email")).sendKeys("joao@email.com");
        driver.findElement(By.id("password")).click();
        driver.findElement(By.id("password")).sendKeys("123456");
        driver.findElement(By.id("administrador")).click();
        assertEquals("Cadastrar",driver.findElement(By.cssSelector(".btn-primary")).getText(),"Texto esperado 'Cadastrar', mas foi exibido outro texto");
        driver.findElement(By.cssSelector(".btn-primary")).click();
        assertEquals("Este email já está sendo usado",driver.findElement(By.cssSelector("div.alert.alert-secondary.alert-dismissible > span")).getText(),"Texto esperado 'Este email já está sendo usado', mas foi exibido outro texto");
    }

}
