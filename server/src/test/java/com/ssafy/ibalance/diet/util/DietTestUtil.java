package com.ssafy.ibalance.diet.util;

import com.ssafy.ibalance.child.entity.Child;
import com.ssafy.ibalance.child.repository.ChildRepository;
import com.ssafy.ibalance.common.TestBase;
import com.ssafy.ibalance.diet.entity.Diet;
import com.ssafy.ibalance.diet.entity.DietMaterial;
import com.ssafy.ibalance.diet.entity.DietMenu;
import com.ssafy.ibalance.diet.repository.diet.DietRepository;
import com.ssafy.ibalance.diet.repository.dietmaterial.DietMaterialRepository;
import com.ssafy.ibalance.diet.repository.dietmenu.DietMenuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public class DietTestUtil extends TestBase {

    @Autowired
    private DietRepository dietRepository;

    @Autowired
    private DietMenuRepository dietMenuRepository;

    @Autowired
    private ChildRepository childRepository;

    @Autowired
    private DietMaterialRepository dietMaterialRepository;

    private final List<String> foodMaterials = List.of("갈치", "치즈", "흰쌀", "무", "다시마", "오징어", "멸치", "디포리", "대파", "다진마늘");

    private final List<String> pickyMaterials = List.of("오징어", "대파");

    public void 식단_메뉴_저장(List<Diet> dietList) {
        List<String> menuIdList = List.of("65fa83bf3eb83d319efa85da", "65fa83f63eb83d319efa85de",
        "65fa83bf3eb83d319efa85b9", "65fa83f63eb83d319efa8629", "65fa83f63eb83d319efa8615",
                "65fa83f63eb83d319efa85e9", "65fa83bf3eb83d319efa85d0", "65fa83f63eb83d319efa8609",
        "65fa84203eb83d319efa868a", "65fa83f63eb83d319efa85ee", "65fa83bf3eb83d319efa85d6",
        "65fa83f63eb83d319efa861d");

        List<DietMenu> dietMenuList = menuIdList.stream()
                .flatMap(menuId -> dietList.stream()
                        .map(diet -> DietMenu.builder()
                                .menuId(menuId)
                                .diet(diet)
                                .build()))
                .toList();

        dietMenuRepository.saveAll(dietMenuList);
    }

    public List<Diet> 식단정보_저장(Integer childId) {

        Child child = childRepository.findById(childId).get();

        List<LocalDate> testDates = List.of(LocalDate.of(2024, 3, 20),
                LocalDate.of(2024, 3, 21), LocalDate.now());

        Integer sequence = 4;

        List<Diet> dietList = testDates.stream().map(date -> Diet.builder()
                .dietDate(date)
                .child(child)
                .sequence(sequence)
                .build()).toList();


        dietList.getFirst().setReviewed(true);
        dietList.getFirst().setDiary("잘 먹었음");

        return dietRepository.saveAll(dietList);
    }

    public void 편식정보_저장(List<Diet> dietList) {
        List<DietMaterial> firstMaterials = foodMaterials.stream()
                .map(m -> DietMaterial.builder()
                        .diet(dietList.getFirst())
                        .material(m)
                        .picky(pickyMaterials.contains(m))
                        .build())
                .toList();

        List<DietMaterial> secondMaterials = foodMaterials.stream()
                .map(m -> DietMaterial.builder()
                        .diet(dietList.getLast())
                        .material(m)
                        .picky(pickyMaterials.getFirst().equals(m))
                        .build())
                .toList();

        dietMaterialRepository.saveAll(firstMaterials);
        dietMaterialRepository.saveAll(secondMaterials);
    }
}
