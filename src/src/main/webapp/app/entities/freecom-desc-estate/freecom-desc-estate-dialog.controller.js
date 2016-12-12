(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Freecom_desc_estateDialogController', Freecom_desc_estateDialogController);

    Freecom_desc_estateDialogController.$inject = ['$uibModalInstance', 'entity', 'C_type_estate', 'C_colony', 'C_municipality', 'C_state', 'C_country', 'C_zip_code'];

    function Freecom_desc_estateDialogController ($uibModalInstance, entity, C_type_estate, C_colony, C_municipality, C_state, C_country, C_zip_code) {
        var vm = this;
        vm.freecom_desc_estate = entity;
        vm.c_type_estates = C_type_estate.query();
        vm.c_countrys = C_country.query({pg:1, filtername:" "});
        vm.c_states = C_state.query({countryId:151, filtername:" "});
        vm.c_municipalitys = null;
        vm.c_colonys = null;

        vm.onChangeC_country = function() {
            var countryId = vm.freecom_desc_estate.c_country.id;
            C_state.query({countryId: countryId, filtername:" "}, function(result){
                vm.c_states = result;
            });
        };

        vm.onChangeC_state = function() {
            var stateId = vm.freecom_desc_estate.c_state.id;
            C_municipality.query({stateId: stateId, filtername:" "}, function(result){
                vm.c_municipalitys = result;
            });
        };

        vm.onChangeC_municipality = function() {
            var municipalityId = vm.freecom_desc_estate.c_municipality.id;
            C_colony.query({municipalityId: municipalityId, filtername:" "}, function(result){
                vm.c_colonys = result;
            });
        };

        vm.onChangeC_colony = function(){
            C_zip_code.get({id : vm.freecom_desc_estate.c_colony.c_zip_code.id}, function(result) {
                console.log(result);
                vm.freecom_desc_estate.c_zip_code = result;
            });
        };

        vm.save = function () {
            vm.isSaving = true;
            $uibModalInstance.close(vm.freecom_desc_estate);
            vm.isSaving = false;
        };

        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
    }
})();
