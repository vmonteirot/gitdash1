package br.com.fiap.gitdash.github;

import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class GitHubController {

    private final GitHubService gitHubService;

    public GitHubController(GitHubService gitHubService) {
        this.gitHubService = gitHubService;
    }

    @GetMapping("/user")
    public String getUserInfo(Model model, OAuth2AuthenticationToken authentication) {
        var userInfo = authentication.getPrincipal().getAttributes();
        String userName = (String) userInfo.get("name");
        String userAvatar = (String) userInfo.get("avatar_url");
        String userHtmlUrl = (String) userInfo.get("html_url");

        model.addAttribute("userName", userName);
        model.addAttribute("userAvatar", userAvatar);
        model.addAttribute("userHtmlUrl", userHtmlUrl);

        // Obtenção da lista de repositórios
        List<RepositoryInfo> repositories = gitHubService.getUserRepositories(userInfo.get("login").toString());
        model.addAttribute("repositories", repositories);

        return "user";
    }
}
