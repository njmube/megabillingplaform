(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('C_acquired_titleDialogController', C_acquired_titleDialogController);

    C_acquired_titleDialogController.$inject = ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'C_acquired_title'];

    function C_acquired_titleDialogController ($scope, $stateParams, $uibModalInstance, entity, C_acquired_title) {
        var vm = this;
        vm.c_acquired_title = entity;
        vm.load = function(id) {
            C_acquired_title.get({id : id}, function(result) {
                vm.c_acquired_title = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('megabillingplatformApp:c_acquired_titleUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        };

        var onSaveError = function () {
            vm.isSaving = false;
        };

        vm.save = function () {
            vm.isSaving = true;
            if (vm.c_acquired_title.id !== null) {
                C_acquired_title.update(vm.c_acquired_title, onSaveSuccess, onSaveError);
            } else {
                C_acquired_title.save(vm.c_acquired_title, onSaveSuccess, onSaveError);
            }
        };

        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
    }
})();
