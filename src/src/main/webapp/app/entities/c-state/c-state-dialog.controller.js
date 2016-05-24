(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('C_stateDialogController', C_stateDialogController);

    C_stateDialogController.$inject = ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'C_state', 'C_country', 'C_municipality'];

    function C_stateDialogController ($scope, $stateParams, $uibModalInstance, entity, C_state, C_country, C_municipality) {
        var vm = this;
        vm.c_state = entity;
        vm.c_countrys = C_country.query();
        vm.c_municipalitys = C_municipality.query();
        vm.load = function(id) {
            C_state.get({id : id}, function(result) {
                vm.c_state = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('megabillingplatformApp:c_stateUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        };

        var onSaveError = function () {
            vm.isSaving = false;
        };

        vm.save = function () {
            vm.isSaving = true;
            if (vm.c_state.id !== null) {
                C_state.update(vm.c_state, onSaveSuccess, onSaveError);
            } else {
                C_state.save(vm.c_state, onSaveSuccess, onSaveError);
            }
        };

        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
    }
})();
