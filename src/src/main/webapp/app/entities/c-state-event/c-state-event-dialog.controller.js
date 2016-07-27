(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('C_state_eventDialogController', C_state_eventDialogController);

    C_state_eventDialogController.$inject = ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'C_state_event'];

    function C_state_eventDialogController ($scope, $stateParams, $uibModalInstance, entity, C_state_event) {
        var vm = this;
        vm.c_state_event = entity;
        vm.load = function(id) {
            C_state_event.get({id : id}, function(result) {
                vm.c_state_event = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('megabillingplatformApp:c_state_eventUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        };

        var onSaveError = function () {
            vm.isSaving = false;
        };

        vm.save = function () {
            vm.isSaving = true;
            if (vm.c_state_event.id !== null) {
                C_state_event.update(vm.c_state_event, onSaveSuccess, onSaveError);
            } else {
                C_state_event.save(vm.c_state_event, onSaveSuccess, onSaveError);
            }
        };

        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
    }
})();
