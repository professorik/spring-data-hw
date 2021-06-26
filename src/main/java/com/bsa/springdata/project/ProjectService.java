package com.bsa.springdata.project;

import com.bsa.springdata.project.dto.CreateProjectRequestDto;
import com.bsa.springdata.project.dto.ProjectDto;
import com.bsa.springdata.project.dto.ProjectSummaryDto;
import com.bsa.springdata.team.Team;
import com.bsa.springdata.team.TeamRepository;
import com.bsa.springdata.team.Technology;
import com.bsa.springdata.team.TechnologyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ProjectService {
    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private TechnologyRepository technologyRepository;
    @Autowired
    private TeamRepository teamRepository;

    public List<ProjectDto> findTop5ByTechnology(String technology) {
        return projectRepository.findFiveByTechnology(technology, PageRequest.of(0, 5))
                .stream()
                .map(ProjectDto::fromEntity)
                .collect(Collectors.toList());
    }

    public Optional<Project> findTheBiggest() {
        return Optional.of(projectRepository.findTheBiggestProject(PageRequest.of(0, 1)).get(0));
    }

    public List<ProjectSummaryDto> getSummary() {
        return projectRepository.getSummary();
    }

    public int getCountWithRole(String role) {
        return projectRepository.getCountWithRole(role);
    }

    public Optional<UUID> createWithTeamAndTechnology(CreateProjectRequestDto createProjectRequest) {
        try {
            var technology = technologyRepository.save(Technology.fromDto(createProjectRequest.getTech(),
                    createProjectRequest.getTechDescription(), createProjectRequest.getTechLink()));
            var team = teamRepository.save(Team.fromDto(createProjectRequest.getTeamName(),
                    createProjectRequest.getTeamRoom(), createProjectRequest.getTeamArea(),
                    technology));
            var project = Project.fromDto(createProjectRequest.getProjectName(),
                    createProjectRequest.getProjectDescription(), team);
            team.setProject(project);
            var result = projectRepository.save(project);
            return Optional.of(result.getId());
        } catch (Exception e) {
            return Optional.empty();
        }
    }
}
