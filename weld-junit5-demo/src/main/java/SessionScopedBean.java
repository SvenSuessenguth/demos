import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Inject;
import java.io.Serializable;
import java.util.Optional;

@SessionScoped
public class SessionScopedBean implements Serializable {

  @Inject
  private ApplicationScopedBean applicationScopedBean;

  public Optional<ApplicationScopedBean> getApplicationScopedBean() {
    return Optional.ofNullable(applicationScopedBean);
  }
}
