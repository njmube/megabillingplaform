(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Com_doneesDialogController', Com_doneesDialogController);

    Com_doneesDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'Com_donees', 'Cfdi'];

    function Com_doneesDialogController ($timeout, $scope, $stateParams, $uibModalInstance, $q, entity, Com_donees, Cfdi) {
        var vm = this;
        vm.com_donees = entity;
        vm.cfdis = Cfdi.query({filter: 'com_donees-is-null'});
        $q.all([vm.com_donees.$promise, vm.cfdis.$promise]).then(function() {
            if (!vm.com_donees.cfdi || !vm.com_donees.cfdi.id) {
                return $q.reject();
            }
            return Cfdi.get({id : vm.com_donees.cfdi.id}).$promise;
        }).then(function(cfdi) {
            vm.cfdis.push(cfdi);
        });

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        var onSaveSuccess = function (result) {
            $scope.$emit('megabillingplatformApp:com_doneesUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        };

        var onSaveError = function () {
            vm.isSaving = false;
        };

        vm.save = function () {
            vm.isSaving = true;
            if (vm.com_donees.id !== null) {
                Com_donees.update(vm.com_donees, onSaveSuccess, onSaveError);
            } else {
                Com_donees.save(vm.com_donees, onSaveSuccess, onSaveError);
            }
        };

        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };

        vm.datePickerOpenStatus = {};
        vm.datePickerOpenStatus.date_authorization = false;

        vm.openCalendar = function(date) {
            vm.datePickerOpenStatus[date] = true;
        };
    }
})();
