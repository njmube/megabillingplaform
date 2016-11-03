(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Com_airlineDialogController', Com_airlineDialogController);

    Com_airlineDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'Com_airline', 'Cfdi'];

    function Com_airlineDialogController ($timeout, $scope, $stateParams, $uibModalInstance, $q, entity, Com_airline, Cfdi) {
        var vm = this;
        vm.com_airline = entity;
        vm.cfdis = Cfdi.query({filter: 'com_airline-is-null'});
        $q.all([vm.com_airline.$promise, vm.cfdis.$promise]).then(function() {
            if (!vm.com_airline.cfdi || !vm.com_airline.cfdi.id) {
                return $q.reject();
            }
            return Cfdi.get({id : vm.com_airline.cfdi.id}).$promise;
        }).then(function(cfdi) {
            vm.cfdis.push(cfdi);
        });

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        var onSaveSuccess = function (result) {
            $scope.$emit('megabillingplatformApp:com_airlineUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        };

        var onSaveError = function () {
            vm.isSaving = false;
        };

        vm.save = function () {
            vm.isSaving = true;
            if (vm.com_airline.id !== null) {
                Com_airline.update(vm.com_airline, onSaveSuccess, onSaveError);
            } else {
                Com_airline.save(vm.com_airline, onSaveSuccess, onSaveError);
            }
        };

        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
    }
})();
