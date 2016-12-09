(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Com_data_operationDialogController', Com_data_operationDialogController);

    Com_data_operationDialogController.$inject = ['$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'Com_data_operation', 'Com_public_notaries'];

    function Com_data_operationDialogController ($scope, $stateParams, $uibModalInstance, $q, entity, Com_data_operation, Com_public_notaries) {
        var vm = this;
        vm.com_data_operation = entity;
        vm.com_public_notariess = Com_public_notaries.query({filter: 'com_data_operation-is-null'});
        $q.all([vm.com_data_operation.$promise, vm.com_public_notariess.$promise]).then(function() {
            if (!vm.com_data_operation.com_public_notaries || !vm.com_data_operation.com_public_notaries.id) {
                return $q.reject();
            }
            return Com_public_notaries.get({id : vm.com_data_operation.com_public_notaries.id}).$promise;
        }).then(function(com_public_notaries) {
            vm.com_public_notariess.push(com_public_notaries);
        });
        vm.load = function(id) {
            Com_data_operation.get({id : id}, function(result) {
                vm.com_data_operation = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('megabillingplatformApp:com_data_operationUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        };

        var onSaveError = function () {
            vm.isSaving = false;
        };

        vm.save = function () {
            vm.isSaving = true;
            if (vm.com_data_operation.id !== null) {
                Com_data_operation.update(vm.com_data_operation, onSaveSuccess, onSaveError);
            } else {
                Com_data_operation.save(vm.com_data_operation, onSaveSuccess, onSaveError);
            }
        };

        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };

        vm.datePickerOpenStatus = {};
        vm.datePickerOpenStatus.dateinstnotarial = false;

        vm.openCalendar = function(date) {
            vm.datePickerOpenStatus[date] = true;
        };
    }
})();
