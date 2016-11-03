(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Com_speiDialogController', Com_speiDialogController);

    Com_speiDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'Com_spei', 'Cfdi'];

    function Com_speiDialogController ($timeout, $scope, $stateParams, $uibModalInstance, $q, entity, Com_spei, Cfdi) {
        var vm = this;
        vm.com_spei = entity;
        vm.cfdis = Cfdi.query({filter: 'com_spei-is-null'});
        $q.all([vm.com_spei.$promise, vm.cfdis.$promise]).then(function() {
            if (!vm.com_spei.cfdi || !vm.com_spei.cfdi.id) {
                return $q.reject();
            }
            return Cfdi.get({id : vm.com_spei.cfdi.id}).$promise;
        }).then(function(cfdi) {
            vm.cfdis.push(cfdi);
        });

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        var onSaveSuccess = function (result) {
            $scope.$emit('megabillingplatformApp:com_speiUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        };

        var onSaveError = function () {
            vm.isSaving = false;
        };

        vm.save = function () {
            vm.isSaving = true;
            if (vm.com_spei.id !== null) {
                Com_spei.update(vm.com_spei, onSaveSuccess, onSaveError);
            } else {
                Com_spei.save(vm.com_spei, onSaveSuccess, onSaveError);
            }
        };

        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
    }
})();
