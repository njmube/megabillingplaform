(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Com_public_notariesDialogController', Com_public_notariesDialogController);

    Com_public_notariesDialogController.$inject = ['$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'Com_public_notaries', 'Cfdi'];

    function Com_public_notariesDialogController ($scope, $stateParams, $uibModalInstance, $q, entity, Com_public_notaries, Cfdi) {
        var vm = this;
        vm.com_public_notaries = entity;
        vm.cfdis = Cfdi.query({filter: 'com_public_notaries-is-null'});
        $q.all([vm.com_public_notaries.$promise, vm.cfdis.$promise]).then(function() {
            if (!vm.com_public_notaries.cfdi || !vm.com_public_notaries.cfdi.id) {
                return $q.reject();
            }
            return Cfdi.get({id : vm.com_public_notaries.cfdi.id}).$promise;
        }).then(function(cfdi) {
            vm.cfdis.push(cfdi);
        });
        vm.load = function(id) {
            Com_public_notaries.get({id : id}, function(result) {
                vm.com_public_notaries = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('megabillingplatformApp:com_public_notariesUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        };

        var onSaveError = function () {
            vm.isSaving = false;
        };

        vm.save = function () {
            vm.isSaving = true;
            if (vm.com_public_notaries.id !== null) {
                Com_public_notaries.update(vm.com_public_notaries, onSaveSuccess, onSaveError);
            } else {
                Com_public_notaries.save(vm.com_public_notaries, onSaveSuccess, onSaveError);
            }
        };

        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
    }
})();
