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
@DisplayName("Teste de Cadastro")
public class RegisterTest {

    // WebDriver estático compartilhado entre todos os testes
    private static WebDriver driver;
    // Email fixo utilizado para testar cadastro duplicado
    private static final String emailDuplicado = "joao.duplicado@email.com";
    // Flag para controlar se o email duplicado já foi criado (evita tentativas duplicadas)
    private static boolean emailDuplicadoCriado = false;

    @BeforeAll
    public static void setUp() {
        // Configura o ChromeDriver automaticamente usando WebDriverManager
        WebDriverManager.chromedriver().setup();
        // Instancia o navegador Chrome
        driver = new ChromeDriver();
        // Maximiza a janela do navegador
        driver.manage().window().maximize();
        // Define um tempo de espera implícito de 10 segundos para localizar elementos
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }

    @BeforeEach
    public void loadingPattern() {
        // Garante que o email duplicado existe antes de cada teste
        // Isso permite que os testes funcionem tanto juntos quanto isoladamente
        if (!emailDuplicadoCriado) {
            generateDuplicatedEmail();
            emailDuplicadoCriado = true;
        }

        // Navega para a página de login antes de cada teste
        driver.get("https://front.serverest.dev/login");
    }

    @AfterAll
    public static void tearDown() {
        System.out.println("Teste finalizado!");
        // Verifica se o driver foi inicializado antes de fechar
        if (driver != null) {
            // Fecha o navegador e encerra a sessão do WebDriver
            driver.quit();
        }
    }

    /**
     * Cria o email duplicado que será usado no teste de email duplicado
     * É executado antes dos testes para garantir que o email já existe
     */
    private static void generateDuplicatedEmail() {
        try {
            // Navega para a página de login
            driver.get("https://front.serverest.dev/login");
            // Clica no link "Cadastre-se"
            driver.findElement(By.linkText("Cadastre-se")).click();
            // Preenche o campo nome
            driver.findElement(By.id("nome")).sendKeys("Usuário Teste");
            // Preenche o campo email com o email duplicado
            driver.findElement(By.id("email")).sendKeys(emailDuplicado);
            // Preenche o campo senha
            driver.findElement(By.id("password")).sendKeys("123456");
            // Marca o checkbox de administrador
            driver.findElement(By.id("administrador")).click();
            // Clica no botão de cadastrar
            driver.findElement(By.cssSelector(".btn-primary")).click();

            // Aguarda o cadastro ser processado (aumentado para 2 segundos para maior estabilidade)
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println("Email duplicado criado/verificado com sucesso");
        } catch (Exception e) {
            // Se o email já existe ou ocorrer algum erro, apenas loga a mensagem
            System.out.println("Email já existe ou erro ao criar: " + e.getMessage());
        }
    }

    /**
     * Gera um email único usando UUID para evitar conflitos nos testes
     * @return String com email único no formato joao[uuid]@email.com
     */
    private String generateUniqueEmail() {
        // Gera um UUID e pega os primeiros 8 caracteres
        String uuid = UUID.randomUUID().toString().substring(0, 8);
        // Retorna o email único
        return "joao" + uuid + "@email.com";
    }

    @Test
    @Order(1)
    @DisplayName("Cadastro com sucesso")
    public void testSuccessfulRegistration() {
        // Gera um email único para este teste
        String emailUnico = generateUniqueEmail();

        // Define o tamanho da janela do navegador
        driver.manage().window().setSize(new Dimension(1366, 720));
        // Verifica se o texto do link "Cadastre-se" está correto
        assertEquals("Cadastre-se", driver.findElement(By.linkText("Cadastre-se")).getText());
        // Clica no link "Cadastre-se"
        driver.findElement(By.linkText("Cadastre-se")).click();
        // Clica no campo nome
        driver.findElement(By.id("nome")).click();
        // Preenche o campo nome
        driver.findElement(By.id("nome")).sendKeys("João");
        // Clica no campo email
        driver.findElement(By.id("email")).click();
        // Preenche o campo email com email único
        driver.findElement(By.id("email")).sendKeys(emailUnico);
        // Clica no campo senha
        driver.findElement(By.id("password")).click();
        // Preenche o campo senha
        driver.findElement(By.id("password")).sendKeys("123456");
        // Marca o checkbox de administrador
        driver.findElement(By.id("administrador")).click();
        // Verifica se o texto do botão "Cadastrar" está correto
        assertEquals("Cadastrar", driver.findElement(By.cssSelector(".btn-primary")).getText(),
                "Texto esperado 'Cadastrar', mas foi exibido outro texto");
        // Clica no botão cadastrar
        driver.findElement(By.cssSelector(".btn-primary")).click();
        // Verifica se a mensagem de sucesso foi exibida
        assertEquals("Cadastro realizado com sucesso",
                driver.findElement(By.cssSelector("a.alert-link")).getText(),
                "Texto esperado 'Cadastro realizado com sucesso', mas foi exibido outro texto");
    }

    @Test
    @Order(2)
    @DisplayName("Cadastro com email duplicado")
    public void testDuplicateEmailRegistration() {
        // Define o tamanho da janela do navegador
        driver.manage().window().setSize(new Dimension(1366, 720));
        // Verifica se o texto do link "Cadastre-se" está correto
        assertEquals("Cadastre-se", driver.findElement(By.linkText("Cadastre-se")).getText());
        // Clica no link "Cadastre-se"
        driver.findElement(By.linkText("Cadastre-se")).click();
        // Clica no campo nome
        driver.findElement(By.id("nome")).click();
        // Preenche o campo nome
        driver.findElement(By.id("nome")).sendKeys("João Duplicado");
        // Clica no campo email
        driver.findElement(By.id("email")).click();
        // Preenche o campo email com o email duplicado (já cadastrado no sistema)
        driver.findElement(By.id("email")).sendKeys(emailDuplicado);
        // Clica no campo senha
        driver.findElement(By.id("password")).click();
        // Preenche o campo senha
        driver.findElement(By.id("password")).sendKeys("123456");
        // Marca o checkbox de administrador
        driver.findElement(By.id("administrador")).click();
        // Verifica se o texto do botão "Cadastrar" está correto
        assertEquals("Cadastrar", driver.findElement(By.cssSelector(".btn-primary")).getText(),
                "Texto esperado 'Cadastrar', mas foi exibido outro texto");
        // Clica no botão cadastrar
        driver.findElement(By.cssSelector(".btn-primary")).click();
        // Verifica se a mensagem de erro de email duplicado foi exibida
        assertEquals("Este email já está sendo usado",
                driver.findElement(By.cssSelector("div.alert.alert-secondary.alert-dismissible > span")).getText(),
                "Texto esperado 'Este email já está sendo usado', mas foi exibido outro texto");
    }
}