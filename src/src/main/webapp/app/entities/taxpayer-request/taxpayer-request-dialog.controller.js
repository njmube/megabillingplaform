(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Taxpayer_requestDialogController', Taxpayer_requestDialogController);

    Taxpayer_requestDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Taxpayer_request'];

    function Taxpayer_requestDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Taxpayer_request) {
        var vm = this;

        vm.taxpayer_request = entity;
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
            if (vm.taxpayer_request.id !== null) {
                Taxpayer_request.update(vm.taxpayer_request, onSaveSuccess, onSaveError);
            } else {
                Taxpayer_request.save(vm.taxpayer_request, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('megabillingplatformApp:taxpayer_requestUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
