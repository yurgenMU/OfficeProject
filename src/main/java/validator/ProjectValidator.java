package validator;

import model.Project;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import service.ProjectService;

public class ProjectValidator implements Validator {

    @Autowired
    private ProjectService projectService;

    @Override
    public void validate(Object o, Errors errors) {
        Project project = (Project) o;
        Project proxyProject = (Project) projectService.getByName(project.getName().trim());
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "Required");
        if ((proxyProject != null) &&
                (proxyProject.getId() != project.getId())) {
            errors.rejectValue("name", "Duplicate.project");
        }
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return Project.class.equals(aClass);
    }
}
