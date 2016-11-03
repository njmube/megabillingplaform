(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Com_ineDialogController', Com_ineDialogController);

    Com_ineDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'Com_ine', 'Cfdi', 'C_committee_type', 'C_process_type'];

    function Com_ineDialogController ($timeout, $scope, $stateParams, $uibModalInstance, $q, entity, Com_ine, Cfdi, C_committee_type, C_process_type) {
        var vm = this;
        vm.com_ine = entity;
        vm.cfdis = Cfdi.query({filter: 'com_ine-is-null'});
        $q.all([vm.com_ine.$promise, vm.cfdis.$promise]).then(function() {
            if (!vm.com_ine.cfdi || !vm.com_ine.cfdi.id) {
                return $q.reject();
            }
            return Cfdi.get({id : vm.com_ine.cfdi.id}).$promise;
        }).then(function(cfdi) {
            vm.cfdis.push(cfdi);
        });
        vm.c_committee_types = C_committee_type.query();
        vm.c_process_types = C_process_type.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        var onSaveSuccess = function (result) {
            $scope.$emit('megabillingplatformApp:com_ineUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        };

        var onSaveError = function () {
            vm.isSaving = false;
        };

        vm.save = function () {
            vm.isSaving = true;
            if (vm.com_ine.id !== null) {
                Com_ine.update(vm.com_ine, onSaveSuccess, onSaveError);
            } else {
                Com_ine.save(vm.com_ine, onSaveSuccess, onSaveError);
            }
        };

        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
    }
})();
