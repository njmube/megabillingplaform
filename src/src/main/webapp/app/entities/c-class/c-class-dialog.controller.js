(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('C_classDialogController', C_classDialogController);

    C_classDialogController.$inject = ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'C_class'];

    function C_classDialogController ($scope, $stateParams, $uibModalInstance, entity, C_class) {
        var vm = this;
        vm.c_class = entity;
        vm.load = function(id) {
            C_class.get({id : id}, function(result) {
                vm.c_class = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('megabillingplatformApp:c_classUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        };

        var onSaveError = function () {
            vm.isSaving = false;
        };

        vm.save = function () {
            vm.isSaving = true;
            if (vm.c_class.id !== null) {
                C_class.update(vm.c_class, onSaveSuccess, onSaveError);
            } else {
                C_class.save(vm.c_class, onSaveSuccess, onSaveError);
            }
        };

        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
    }
})();
