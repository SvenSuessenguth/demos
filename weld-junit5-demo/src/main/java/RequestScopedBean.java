import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import java.util.Optional;

@RequestScoped
public class RequestScopedBean {

  @Inject
  private SessionScopedBean sessionScopedBean;

  public Optional<SessionScopedBean> getSessionScopedBean() {
    return Optional.ofNullable(sessionScopedBean);
  }
}
