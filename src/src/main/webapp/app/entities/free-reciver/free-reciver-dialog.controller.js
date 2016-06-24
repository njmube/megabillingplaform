(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Free_reciverDialogController', Free_reciverDialogController);

    Free_reciverDialogController.$inject = ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'Free_reciver', 'C_country', 'C_state', 'C_municipality', 'C_location', 'C_colony', 'C_zip_code'];

    function Free_reciverDialogController ($scope, $stateParams, $uibModalInstance, entity, Free_reciver, C_country, C_state, C_municipality, C_location, C_colony, C_zip_code) {
        var vm = this;
        vm.free_reciver = entity;
        vm.c_countrys = C_country.query();
        vm.c_states = C_state.query();
        vm.c_municipalitys = C_municipality.query();
        vm.c_locations = C_location.query();
        vm.c_colonys = C_colony.query();
        vm.c_zip_codes = C_zip_code.query();
        vm.load = function(id) {
            Free_reciver.get({id : id}, function(result) {
                vm.free_reciver = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('megabillingplatformApp:free_reciverUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        };

        var onSaveError = function () {
            vm.isSaving = false;
        };

        vm.save = function () {
            vm.isSaving = true;
            if (vm.free_reciver.id !== null) {
                Free_reciver.update(vm.free_reciver, onSaveSuccess, onSaveError);
            } else {
                Free_reciver.save(vm.free_reciver, onSaveSuccess, onSaveError);
            }
        };

        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
    }
})();
