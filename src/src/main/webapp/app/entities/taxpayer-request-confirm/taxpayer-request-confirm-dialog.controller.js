(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Taxpayer_request_confirmDialogController', Taxpayer_request_confirmDialogController);

    Taxpayer_request_confirmDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Taxpayer_request_confirm'];

    function Taxpayer_request_confirmDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Taxpayer_request_confirm) {
        var vm = this;

        vm.taxpayer_request_confirm = entity;
        vm.clear = clear;
        vm.save = save;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.taxpayer_request_confirm.id !== null) {
                Taxpayer_request_confirm.update(vm.taxpayer_request_confirm, onSaveSuccess, onSaveError);
            } else {
                Taxpayer_request_confirm.save(vm.taxpayer_request_confirm, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('megabillingplatformApp:taxpayer_request_confirmUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
