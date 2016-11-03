(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Com_incotermDialogController', Com_incotermDialogController);

    Com_incotermDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Com_incoterm'];

    function Com_incotermDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Com_incoterm) {
        var vm = this;
        vm.com_incoterm = entity;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        var onSaveSuccess = function (result) {
            $scope.$emit('megabillingplatformApp:com_incotermUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        };

        var onSaveError = function () {
            vm.isSaving = false;
        };

        vm.save = function () {
            vm.isSaving = true;
            if (vm.com_incoterm.id !== null) {
                Com_incoterm.update(vm.com_incoterm, onSaveSuccess, onSaveError);
            } else {
                Com_incoterm.save(vm.com_incoterm, onSaveSuccess, onSaveError);
            }
        };

        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
    }
})();
