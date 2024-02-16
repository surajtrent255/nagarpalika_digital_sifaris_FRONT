/**
 * @author Umesh Bhujel <yoomesbhujel@gmail.com>
 * Since Feb 26, 2020
 */
package com.ishanitech.ipalikawebapp.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.ishanitech.ipalikawebapp.dto.*;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ishanitech.ipalikawebapp.service.ReportService;
import com.ishanitech.ipalikawebapp.service.WardService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequestMapping("/report")
@Controller
public class ReportController {
	private final ReportService reportService;
	private final WardService wardService;
	
	public ReportController(ReportService reportService, WardService wardService) {
		this.reportService = reportService;
		this.wardService = wardService;
	}

	
//	@Secured({"ROLE_CENTRAL_ADMIN", "ROLE_WARD_ADMIN", "ROLE_SUPER_ADMIN"})
	@GetMapping
	public String getDashboardView(HttpServletRequest request, Model model, @AuthenticationPrincipal UserDTO user) {
		int wardNo = 0;
		String selectedWard = request.getParameter("selectedWard");
		
		if(user == null) {
			if(selectedWard == null) {
				wardNo = 0;
				selectedWard = "0";
			} else {
				wardNo = Integer.parseInt(selectedWard);
			}
		} else if(user.getRoles().contains("WARD_ADMIN")) {
			selectedWard = String.valueOf(user.getWardNo()); 
			wardNo = Integer.parseInt(selectedWard);
		} else if(user.getRoles().contains("CENTRAL_ADMIN") || user.getRoles().contains("SUPER_ADMIN")) {
			if(selectedWard == null) {
				wardNo = 0;
				selectedWard = "0";
			} else {
				wardNo = Integer.parseInt(selectedWard);
			}
		}
		
		model.addAttribute("wards", wardService.getAllWards());
		if(user != null ) {
			model.addAttribute("loggedInUserWard", user.getWardNo());
		}
		model.addAttribute("selectedWard", selectedWard);
		
		List<PopulationReport> populationReport = reportService.getPopulationReport(wardNo);
		
		if(!populationReport.isEmpty() && populationReport.size() > 0) {
			// get(0) point to infants, totalChildrens, totalYouths, totalMidage, totalOldAge, and totalSeniorCitizens
			double totalInfants = populationReport.get(0).getOption1();
			double totalChildrens = populationReport.get(0).getOption2();
			double totalYouths = populationReport.get(0).getOption3();
			double totalMidage = populationReport.get(0).getOption4();;
			double totalOldage = populationReport.get(0).getOption5();
			double totalSeniorCitizens = populationReport.get(0).getOption6();
			
			// get(1) always point to male female and total population 
			double totalMale = populationReport.get(1).getOption1();
			double totalFemale = populationReport.get(1).getOption2();
			double totalOthers = populationReport.get(1).getOption3(); 
			
			model.addAttribute("populationReport", populationReport);
			model.addAttribute("totalInfants", totalInfants);
			model.addAttribute("totalChildrens", totalChildrens);
			model.addAttribute("totalYouths", totalYouths);
			model.addAttribute("totalMidage", totalMidage);
			model.addAttribute("totalOldage", totalOldage);
			model.addAttribute("totalSeniorCitizens", totalSeniorCitizens);
			model.addAttribute("totalMale", totalMale);
			model.addAttribute("totalFemale", totalFemale);
			model.addAttribute("totalOthers", totalOthers);
		}
		model.addAttribute("questionReport", reportService.getQuestionReport(wardNo));
		model.addAttribute("extraReport", reportService.getExtraReport(wardNo));
		model.addAttribute("favPlaceReport", reportService.getFavPlaceReport(wardNo));
		return "dashboard";
	}
	
	@PostMapping("/{wardNo}")
	public String generateReport(@AuthenticationPrincipal UserDTO user, @PathVariable("wardNo") int wardNo) {
		log.info("wardNumber sent " + wardNo);
		reportService.generateReport(wardNo, user.getToken());
		return "private/common/dashboard";
	}
	
	
	@GetMapping("/beekeeping/{wardNo}")
	public String getBeekeepingReportView(@PathVariable("wardNo") int wardNo, Model model) {
		Response<List<BeekeepingDTO>> reportResponse = reportService.getBeekeepingInfo(wardNo);
		model.addAttribute("beekeepingList", reportResponse.getData());
		return "private/common/report-beekeeping";
	}
	
	@GetMapping("/agriculturalFarm/{wardNo}")
	public String getAgriculturalFarmReportView(@PathVariable("wardNo") int wardNo, Model model) {
		Response<List<AgriculturalFarmDTO>> reportResponse = reportService.getAgriculturalFarmInfo(wardNo);
		model.addAttribute("agriculturalFarmList", reportResponse.getData());
		return "private/common/report-agricultural-farm";
	}

	@GetMapping("/sisu")
	public String getSisuReportView(Model model){
		Response<List<AgeGroupDTO>>  sisuReportResponse = reportService.getSisuReport();
		model.addAttribute("sisuList", sisuReportResponse.getData());
		return "private/common/report-sisu";
	}


	@GetMapping("balbalika/")
	public String getBalbalikaReportView(Model model){
		Response<List<AgeGroupDTO>> balBalikaReportResponse = reportService.getBallBalikaReport();
		model.addAttribute("balbalikaList", balBalikaReportResponse.getData());
		return "private/common/report-balbalika";
	}


	@GetMapping("yuwa/")
	public String getYuwaReportView(Model model){
		Response<List<AgeGroupDTO>> yuwaReportResponse = reportService.getYuwaReport();
		model.addAttribute("yuwaList", yuwaReportResponse.getData());
		return "private/common/report-yuwa";
	}

	@GetMapping("adhBaisa/")
	public String getAdhBaisaReportView(Model model){
		Response<List<AgeGroupDTO>> adhBaisaReportResponse = reportService.getAdhBaisaReport();
		model.addAttribute("adhBaisaList", adhBaisaReportResponse.getData());
		return "private/common/report-adhBaisa";
	}

	@GetMapping("briddha/")
	public String getBriddhaReportView(Model model){
		Response<List<AgeGroupDTO>> briddhaReportResponse = reportService.getBriddhaReport();
		model.addAttribute("briddhaList", briddhaReportResponse.getData());
		return "private/common/report-briddha";
	}

	@GetMapping("jesthaNagarik/")
	public String getJesthaNagarikReportView(Model model){
		Response<List<AgeGroupDTO>> jesthaNagarikReportResponse = reportService.getJesthaNagarikReport();
		model.addAttribute("jesthaNagarikList", jesthaNagarikReportResponse.getData());
		return "private/common/report-jesthaNagarik";
	}


	@GetMapping("purkheuliVasa/{vasaId}")
	public String getPurkheuliVasaReportView(@PathVariable("vasaId") String vasaId, Model model){
		Response<List<LifeStyleAndCultureDTO>> purkheuliVasaReportResponse = reportService.getPurkheuliVasaReport(Integer.parseInt(vasaId));
		model.addAttribute("vasa", purkheuliVasaReportResponse.getData().get(0).getMotherTongue());
		model.addAttribute("purkheuliVasaReportList", purkheuliVasaReportResponse.getData()) ;
		return "private/common/report-pukheulivasa";
	}


	@GetMapping("favourite-place-type/{typeId}")
	public String getFavouritePlaceTypeReportView(@PathVariable("typeId") String typeId, Model model){
		Response<List<FavouritePlaceTypeDTO>> favouritePlaceTypeReportResponse = reportService.getFavouritePlaceTypeReport(Integer.parseInt(typeId));
		model.addAttribute("favouritePlaceTypeReportList", favouritePlaceTypeReportResponse.getData());
		return "private/common/report-favourite-place-type";
	}
	@GetMapping("religion/{religionId}")
	public String getReligionHouseholdGroupReportView(@PathVariable("religionId") String religionId, Model model){
		Response<List<LifeStyleAndCultureDTO>>religionHouseholdReportResponse = reportService.getReligionHouseholdGroupReport(Integer.parseInt(religionId));
		model.addAttribute("religion", religionHouseholdReportResponse.getData().get(0).getReligion());
		model.addAttribute("religionHouseholdReportList", religionHouseholdReportResponse.getData()) ;
		return "private/common/report-religion-household";
	}

	@GetMapping("caste/{casteId}")
	public String getCasteHouseholdGroupReportView(@PathVariable("casteId") String casteId, Model model){
		Response<List<LifeStyleAndCultureDTO>> casteHouseholdReportResponse = reportService.getCasteHouseholdGroupReport(Integer.parseInt(casteId));
		model.addAttribute("caste", casteHouseholdReportResponse.getData().get(0).getCaste());
		model.addAttribute("casteHouseholdReportList", casteHouseholdReportResponse.getData()) ;
		return "private/common/report-caste-household";
	}

	@GetMapping("academicQualification/{qualificationType}")
	public String getAcademicQualificationReportView(@PathVariable("qualificationType") String qualType, Model model){
		Response<List<AgeGroupDTO>> acadQualReportResponse = reportService.getAcademicQualificationReport(qualType);
		model.addAttribute("qualificationType", qualType);
		model.addAttribute("academicList", acadQualReportResponse.getData());
		return "private/common/report-academicQualification";
	}

	@GetMapping("barsikIncomeExpenses/{id}/{type}")
	public String getBarsikIncomeExpensesReportView(@PathVariable("id") String id, @PathVariable("type") String type, Model model){
		Response<List<LifeStyleAndCultureDTO>> incomeExpensesReportResponse = reportService.getBarsikIncomeExpensesReport(id, type);
		model.addAttribute("barsikIncomeExpensesReportList", incomeExpensesReportResponse.getData());
		if(type.equals("inc")){

			model.addAttribute("inc", "Income");

		} else if (type.equals("exp")){

			model.addAttribute("exp", "Expense");
		}
		return "private/common/report-income-expenses";
	}


	@GetMapping("houseHoldMedicalApproaches/{id}")
	public String getHouseHoldMedicalApproachesReportView(@PathVariable("id") String id, Model model){
		Response<List<LifeStyleAndCultureDTO>> houseHoldMedicalApproachReport = reportService.getHouseHoldMedicalApproachReport(id);
		model.addAttribute("houseHoldMedicalApproachList", houseHoldMedicalApproachReport.getData());
		return "private/common/report-household-medical-approach";
	}

	@GetMapping("houseHoldInfantMortality/{statusId}")
	public String getHouseholdInfantMortalityReportView(@PathVariable("statusId") String id, Model model){
		Response<List<LifeStyleAndCultureDTO>> householdInfantMortalityReport = reportService.getHouseholdInfantMortalityReport(id);
		model.addAttribute("householdInfantMortalityReportList", householdInfantMortalityReport.getData());
		return "private/common/report-household-infant-mortality";
	}

	@GetMapping("/agriculturalCrop/{wardNo}")
	public String getAgriculturalCropReportView(@PathVariable("wardNo") int wardNo, Model model) {
		model.addAttribute("agriculturalCropReport", reportService.getExtraReport(wardNo));
		return "private/common/report-agricultural-crop";
	}
	
	@GetMapping("/animals/{wardNo}")
	public String getAnimalsReportView(@PathVariable("wardNo") int wardNo, Model model) {
		model.addAttribute("animalsReport", reportService.getQuestionReport(wardNo));
		return "private/common/report-animal";
	}
	
}
