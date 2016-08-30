(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Tax_address_requestDialogController', Tax_address_requestDialogController);

    Tax_address_requestDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Tax_address_request', 'C_country', 'C_state', 'C_municipality', 'C_colony', 'C_zip_code'];

    function Tax_address_requestDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Tax_address_request, C_country, C_state, C_municipality, C_colony, C_zip_code) {
        var vm = this;

        vm.tax_address_request = entity;
        vm.clear = clear;
        vm.save = save;
        vm.c_countries = C_country.query();
        vm.c_states = C_state.query();
        vm.c_municipalities = C_municipality.query();
        vm.c_colonies = C_colony.query();
        vm.c_zip_codes = C_zip_code.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.tax_address_request.id !== null) {
                Tax_address_request.update(vm.tax_address_request, onSaveSuccess, onSaveError);
            } else {
                Tax_address_request.save(vm.tax_address_request, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('megabillingplatformApp:tax_address_requestUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
