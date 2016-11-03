(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Com_taxlegendsDialogController', Com_taxlegendsDialogController);

    Com_taxlegendsDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'Com_taxlegends', 'Cfdi'];

    function Com_taxlegendsDialogController ($timeout, $scope, $stateParams, $uibModalInstance, $q, entity, Com_taxlegends, Cfdi) {
        var vm = this;
        vm.com_taxlegends = entity;
        vm.cfdis = Cfdi.query({filter: 'com_taxlegends-is-null'});
        $q.all([vm.com_taxlegends.$promise, vm.cfdis.$promise]).then(function() {
            if (!vm.com_taxlegends.cfdi || !vm.com_taxlegends.cfdi.id) {
                return $q.reject();
            }
            return Cfdi.get({id : vm.com_taxlegends.cfdi.id}).$promise;
        }).then(function(cfdi) {
            vm.cfdis.push(cfdi);
        });

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        var onSaveSuccess = function (result) {
            $scope.$emit('megabillingplatformApp:com_taxlegendsUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        };

        var onSaveError = function () {
            vm.isSaving = false;
        };

        vm.save = function () {
            vm.isSaving = true;
            if (vm.com_taxlegends.id !== null) {
                Com_taxlegends.update(vm.com_taxlegends, onSaveSuccess, onSaveError);
            } else {
                Com_taxlegends.save(vm.com_taxlegends, onSaveSuccess, onSaveError);
            }
        };

        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
    }
})();
