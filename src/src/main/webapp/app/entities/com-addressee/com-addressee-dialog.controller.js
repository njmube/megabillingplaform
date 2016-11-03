(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Com_addresseeDialogController', Com_addresseeDialogController);

    Com_addresseeDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Com_addressee', 'C_country', 'C_state', 'C_municipality', 'C_colony', 'C_zip_code'];

    function Com_addresseeDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Com_addressee, C_country, C_state, C_municipality, C_colony, C_zip_code) {
        var vm = this;
        vm.com_addressee = entity;
        vm.c_countries = C_country.query();
        vm.c_states = C_state.query();
        vm.c_municipalities = C_municipality.query();
        vm.c_colonies = C_colony.query();
        vm.c_zip_codes = C_zip_code.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        var onSaveSuccess = function (result) {
            $scope.$emit('megabillingplatformApp:com_addresseeUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        };

        var onSaveError = function () {
            vm.isSaving = false;
        };

        vm.save = function () {
            vm.isSaving = true;
            if (vm.com_addressee.id !== null) {
                Com_addressee.update(vm.com_addressee, onSaveSuccess, onSaveError);
            } else {
                Com_addressee.save(vm.com_addressee, onSaveSuccess, onSaveError);
            }
        };

        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
    }
})();
