import static org.assertj.core.api.Assertions.assertThat;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.RequestScoped;
import jakarta.enterprise.context.SessionScoped;
import org.jboss.weld.junit5.WeldInitiator;
import org.jboss.weld.junit5.WeldJunit5Extension;
import org.jboss.weld.junit5.WeldSetup;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(WeldJunit5Extension.class)
class WeldJunitTest {

  @WeldSetup
  public WeldInitiator weld = WeldInitiator
      .from(RequestScopedBean.class, SessionScopedBean.class, ApplicationScopedBean.class)
      .activate(RequestScoped.class, SessionScoped.class, ApplicationScoped.class)
      .build();

  @Test
  void testRequestScopedBean_withWeldJunit() {
    RequestScopedBean rsBean = weld.select(RequestScopedBean.class).get();

    assertThat(rsBean).isNotNull();
    assertThat(rsBean.getSessionScopedBean()).isPresent();
    assertThat(rsBean.getSessionScopedBean().get().getApplicationScopedBean()).isPresent();
  }

  @Test
  void testRequestScopedBean_withoutWeldJunit() {
    RequestScopedBean rsBean = new RequestScopedBean();

    assertThat(rsBean).isNotNull();
    assertThat(rsBean.getSessionScopedBean()).isEmpty();
  }
}
