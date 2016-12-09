(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Com_desc_stateDialogController', Com_desc_stateDialogController);

    Com_desc_stateDialogController.$inject = ['$uibModalInstance', 'entity', 'Type_state', 'C_colony', 'C_municipality', 'C_state', 'C_country', 'C_zip_code'];

    function Com_desc_stateDialogController ($uibModalInstance, entity, Type_state, C_colony, C_municipality, C_state, C_country, C_zip_code) {
        var vm = this;
        vm.com_desc_state = entity;
        vm.type_states = Type_state.query();
        vm.c_countrys = C_country.query({pg:1, filtername:" "});
        vm.c_states = C_state.query({countryId:151, filtername:" "});
        vm.c_municipalitys = null;
        vm.c_colonys = null;

        vm.onChangeC_country = function() {
            var countryId = vm.com_desc_state.c_country.id;
            C_state.query({countryId: countryId, filtername:" "}, function(result){
                vm.c_states = result;
            });
        };

        vm.onChangeC_state = function() {
            var stateId = vm.com_desc_state.c_state.id;
            C_municipality.query({stateId: stateId, filtername:" "}, function(result){
                vm.c_municipalitys = result;
            });
        };

        vm.onChangeC_municipality = function() {
            var municipalityId = vm.com_desc_state.c_municipality.id;
            C_colony.query({municipalityId: municipalityId, filtername:" "}, function(result){
                vm.c_colonys = result;
            });
        };

        vm.onChangeC_colony = function(){
            C_zip_code.get({id : vm.com_desc_state.c_colony.c_zip_code.id}, function(result) {
                console.log(result);
                vm.com_desc_state.c_zip_code = result;
            });
        };

        vm.save = function () {
            vm.isSaving = true;
            $uibModalInstance.close(vm.com_desc_state);
            vm.isSaving = false;
        };

        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
    }
})();
