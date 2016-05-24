(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Request_stateDialogController', Request_stateDialogController);

    Request_stateDialogController.$inject = ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'Request_state'];

    function Request_stateDialogController ($scope, $stateParams, $uibModalInstance, entity, Request_state) {
        var vm = this;
        vm.request_state = entity;
        vm.load = function(id) {
            Request_state.get({id : id}, function(result) {
                vm.request_state = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('megabillingplatformApp:request_stateUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        };

        var onSaveError = function () {
            vm.isSaving = false;
        };

        vm.save = function () {
            vm.isSaving = true;
            if (vm.request_state.id !== null) {
                Request_state.update(vm.request_state, onSaveSuccess, onSaveError);
            } else {
                Request_state.save(vm.request_state, onSaveSuccess, onSaveError);
            }
        };

        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
    }
})();
