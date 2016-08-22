(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Freecom_addresseeDialogController', Freecom_addresseeDialogController);

    Freecom_addresseeDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Freecom_addressee', 'C_country', 'C_state', 'C_municipality', 'C_colony', 'C_zip_code'];

    function Freecom_addresseeDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Freecom_addressee, C_country, C_state, C_municipality, C_colony, C_zip_code) {
        var vm = this;

        vm.freecom_addressee = entity;
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
            if (vm.freecom_addressee.id !== null) {
                Freecom_addressee.update(vm.freecom_addressee, onSaveSuccess, onSaveError);
            } else {
                Freecom_addressee.save(vm.freecom_addressee, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('megabillingplatformApp:freecom_addresseeUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
