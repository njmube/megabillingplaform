(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('ScopeDialogController', ScopeDialogController);

    ScopeDialogController.$inject = ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'Scope'];

    function ScopeDialogController ($scope, $stateParams, $uibModalInstance, entity, Scope) {
        var vm = this;
        vm.scope = entity;
        vm.load = function(id) {
            Scope.get({id : id}, function(result) {
                vm.scope = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('megabillingplatformApp:scopeUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        };

        var onSaveError = function () {
            vm.isSaving = false;
        };

        vm.save = function () {
            vm.isSaving = true;
            if (vm.scope.id !== null) {
                Scope.update(vm.scope, onSaveSuccess, onSaveError);
            } else {
                Scope.save(vm.scope, onSaveSuccess, onSaveError);
            }
        };

        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
    }
})();
