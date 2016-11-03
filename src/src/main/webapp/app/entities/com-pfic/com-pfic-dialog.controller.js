(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Com_pficDialogController', Com_pficDialogController);

    Com_pficDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'Com_pfic', 'Cfdi'];

    function Com_pficDialogController ($timeout, $scope, $stateParams, $uibModalInstance, $q, entity, Com_pfic, Cfdi) {
        var vm = this;
        vm.com_pfic = entity;
        vm.cfdis = Cfdi.query({filter: 'com_pfic-is-null'});
        $q.all([vm.com_pfic.$promise, vm.cfdis.$promise]).then(function() {
            if (!vm.com_pfic.cfdi || !vm.com_pfic.cfdi.id) {
                return $q.reject();
            }
            return Cfdi.get({id : vm.com_pfic.cfdi.id}).$promise;
        }).then(function(cfdi) {
            vm.cfdis.push(cfdi);
        });

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        var onSaveSuccess = function (result) {
            $scope.$emit('megabillingplatformApp:com_pficUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        };

        var onSaveError = function () {
            vm.isSaving = false;
        };

        vm.save = function () {
            vm.isSaving = true;
            if (vm.com_pfic.id !== null) {
                Com_pfic.update(vm.com_pfic, onSaveSuccess, onSaveError);
            } else {
                Com_pfic.save(vm.com_pfic, onSaveSuccess, onSaveError);
            }
        };

        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
    }
})();
