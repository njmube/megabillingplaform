(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('TaskDialogController', TaskDialogController);

    TaskDialogController.$inject = ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'Task'];

    function TaskDialogController ($scope, $stateParams, $uibModalInstance, entity, Task) {
        var vm = this;
        vm.task = entity;
        vm.load = function(id) {
            Task.get({id : id}, function(result) {
                vm.task = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('megabillingplatformApp:taskUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        };

        var onSaveError = function () {
            vm.isSaving = false;
        };

        vm.save = function () {
            vm.isSaving = true;
            if (vm.task.id !== null) {
                Task.update(vm.task, onSaveSuccess, onSaveError);
            } else {
                Task.save(vm.task, onSaveSuccess, onSaveError);
            }
        };

        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
    }
})();
