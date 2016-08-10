(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Freecom_speiDialogController', Freecom_speiDialogController);

    Freecom_speiDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'Freecom_spei', 'Free_cfdi'];

    function Freecom_speiDialogController ($timeout, $scope, $stateParams, $uibModalInstance, $q, entity, Freecom_spei, Free_cfdi) {
        var vm = this;

        vm.freecom_spei = entity;
        vm.clear = clear;
        vm.save = save;
        vm.free_cfdis = Free_cfdi.query({filter: 'freecom_spei-is-null'});
        $q.all([vm.freecom_spei.$promise, vm.free_cfdis.$promise]).then(function() {
            if (!vm.freecom_spei.free_cfdi || !vm.freecom_spei.free_cfdi.id) {
                return $q.reject();
            }
            return Free_cfdi.get({id : vm.freecom_spei.free_cfdi.id}).$promise;
        }).then(function(free_cfdi) {
            vm.free_cfdis.push(free_cfdi);
        });

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.freecom_spei.id !== null) {
                Freecom_spei.update(vm.freecom_spei, onSaveSuccess, onSaveError);
            } else {
                Freecom_spei.save(vm.freecom_spei, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('megabillingplatformApp:freecom_speiUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
