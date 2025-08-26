import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import java.time.Duration;
import static org.junit.jupiter.api.Assertions.assertEquals;


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
    public void setUpTest() {
        driver.get("https://front.serverest.dev/login");
    }

    @AfterAll
    public static void tearDown() {
        System.out.println("Teste finalizado!");
        driver.quit();
    }

    @Test
    @DisplayName("Cadastro com sucesso")
    public void testCadastroComSucesso() {
        driver.get("https://front.serverest.dev/login");
        driver.manage().window().setSize(new Dimension(1366, 720));
        assertEquals("Cadastre-se", driver.findElement(By.linkText("Cadastre-se")).getText());
        driver.findElement(By.linkText("Cadastre-se")).click();
        driver.findElement(By.id("nome")).click();
        driver.findElement(By.id("nome")).sendKeys("João");
        driver.findElement(By.id("email")).click();
        driver.findElement(By.id("email")).sendKeys("joaoa@email.com");
        driver.findElement(By.id("password")).click();
        driver.findElement(By.id("password")).sendKeys("123456");
        driver.findElement(By.id("administrador")).click();
        driver.findElement(By.cssSelector(".btn-primary")).click();
        driver.findElement(By.cssSelector(".lead")).click();
        assertEquals("Este é seu sistema para administrar seu ecommerce.",driver.findElement(By.cssSelector(".lead")).getText());
    }

}
