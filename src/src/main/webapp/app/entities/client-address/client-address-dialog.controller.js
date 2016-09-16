(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Client_addressDialogController', Client_addressDialogController);

    Client_addressDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Client_address', 'C_country', 'C_state', 'C_municipality', 'C_colony', 'C_zip_code'];

    function Client_addressDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Client_address, C_country, C_state, C_municipality, C_colony, C_zip_code) {
        var vm = this;

        vm.client_address = entity;
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
            if (vm.client_address.id !== null) {
                Client_address.update(vm.client_address, onSaveSuccess, onSaveError);
            } else {
                Client_address.save(vm.client_address, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('megabillingplatformApp:client_addressUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
