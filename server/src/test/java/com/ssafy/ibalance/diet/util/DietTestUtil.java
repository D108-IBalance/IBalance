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
import java.util.stream.IntStream;

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

    private final List<String> foodMaterials = List.of("쌀", "대추", "양파", "마늘", "닭고기", "크림", "후추",
            "조선무", "논벼", "수수", "참기름", "대파", "인삼", "기장", "명태", "떡", "밤", "다시마", "생과", "계란", "설탕", "멥쌀",
            "소고기", "고추가루", "당근", "멸치", "백설탕", "양송이", "버터", "수삼", "찹쌀", "밀가루");


    private final List<String> pickyMaterials = List.of("대추", "마늘");

    public List<DietMenu> 식단_메뉴_저장(List<Diet> dietList) {
        List<String> menuIdList = List.of("65fa83bf3eb83d319efa85da", "65fa83f63eb83d319efa85de",
                "65fa83bf3eb83d319efa85b9", "65fa83f63eb83d319efa8629", "65fa83f63eb83d319efa8615",
                "65fa83f63eb83d319efa85e9", "65fa83bf3eb83d319efa85d0", "65fa83f63eb83d319efa8609",
                "65fa84203eb83d319efa868a", "65fa83f63eb83d319efa85ee", "65fa83bf3eb83d319efa85d6",
                "65fa83f63eb83d319efa861d");

        List<DietMenu> dietMenuList = IntStream.range(0, menuIdList.size())
                .mapToObj(i -> DietMenu.builder()
                        .menuId(menuIdList.get(i))
                        .diet(dietList.get(i % 3))
                        .build())
                .toList();

        return dietMenuRepository.saveAll(dietMenuList);
    }

    public List<Diet> 식단정보_저장(Integer childId) {

        Child child = childRepository.findById(childId).get();

        List<LocalDate> testDates = List.of(LocalDate.of(2024, 3, 20),
                LocalDate.of(2024, 3, 21), LocalDate.now());

        List<Diet> dietList = testDates.stream().map(date -> Diet.builder()
                .dietDate(date)
                .child(child)
                .build()).toList();


        dietList.getFirst().setReviewed(true);
        dietList.getFirst().setDiary("잘 먹었음");

        return dietRepository.saveAll(dietList);
    }

    public List<DietMaterial> 편식정보_저장(List<Diet> dietList, boolean realSave) {
        List<DietMaterial> firstMaterials = foodMaterials.stream()
                .map(m -> DietMaterial.builder()
                        .diet(dietList.getFirst())
                        .material(m)
                        .picky(pickyMaterials.contains(m) && realSave)
                        .build())
                .toList();

        return dietMaterialRepository.saveAll(firstMaterials);
    }
}
