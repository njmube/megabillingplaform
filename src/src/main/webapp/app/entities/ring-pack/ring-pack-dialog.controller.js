(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Ring_packDialogController', Ring_packDialogController);

    Ring_packDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Ring_pack'];

    function Ring_packDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Ring_pack) {
        var vm = this;

        vm.ring_pack = entity;
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
            if (vm.ring_pack.id !== null) {
                Ring_pack.update(vm.ring_pack, onSaveSuccess, onSaveError);
            } else {
                Ring_pack.save(vm.ring_pack, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('megabillingplatformApp:ring_packUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
