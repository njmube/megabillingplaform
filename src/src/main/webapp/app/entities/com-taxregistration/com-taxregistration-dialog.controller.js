(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Com_taxregistrationDialogController', Com_taxregistrationDialogController);

    Com_taxregistrationDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'Com_taxregistration', 'Cfdi'];

    function Com_taxregistrationDialogController ($timeout, $scope, $stateParams, $uibModalInstance, $q, entity, Com_taxregistration, Cfdi) {
        var vm = this;
        vm.com_taxregistration = entity;
        vm.cfdis = Cfdi.query({filter: 'com_taxregistration-is-null'});
        $q.all([vm.com_taxregistration.$promise, vm.cfdis.$promise]).then(function() {
            if (!vm.com_taxregistration.cfdi || !vm.com_taxregistration.cfdi.id) {
                return $q.reject();
            }
            return Cfdi.get({id : vm.com_taxregistration.cfdi.id}).$promise;
        }).then(function(cfdi) {
            vm.cfdis.push(cfdi);
        });

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        var onSaveSuccess = function (result) {
            $scope.$emit('megabillingplatformApp:com_taxregistrationUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        };

        var onSaveError = function () {
            vm.isSaving = false;
        };

        vm.save = function () {
            vm.isSaving = true;
            if (vm.com_taxregistration.id !== null) {
                Com_taxregistration.update(vm.com_taxregistration, onSaveSuccess, onSaveError);
            } else {
                Com_taxregistration.save(vm.com_taxregistration, onSaveSuccess, onSaveError);
            }
        };

        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
    }
})();
