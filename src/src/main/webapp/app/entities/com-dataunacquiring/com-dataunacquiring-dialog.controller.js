(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Com_dataunacquiringDialogController', Com_dataunacquiringDialogController);

    Com_dataunacquiringDialogController.$inject = ['$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'Com_dataunacquiring', 'Com_acquiring_data'];

    function Com_dataunacquiringDialogController ($scope, $stateParams, $uibModalInstance, $q, entity, Com_dataunacquiring, Com_acquiring_data) {
        var vm = this;
        vm.com_dataunacquiring = entity;
        vm.com_acquiring_datas = Com_acquiring_data.query({filter: 'com_dataunacquiring-is-null'});
        $q.all([vm.com_dataunacquiring.$promise, vm.com_acquiring_datas.$promise]).then(function() {
            if (!vm.com_dataunacquiring.com_acquiring_data || !vm.com_dataunacquiring.com_acquiring_data.id) {
                return $q.reject();
            }
            return Com_acquiring_data.get({id : vm.com_dataunacquiring.com_acquiring_data.id}).$promise;
        }).then(function(com_acquiring_data) {
            vm.com_acquiring_datas.push(com_acquiring_data);
        });
        vm.load = function(id) {
            Com_dataunacquiring.get({id : id}, function(result) {
                vm.com_dataunacquiring = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('megabillingplatformApp:com_dataunacquiringUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        };

        var onSaveError = function () {
            vm.isSaving = false;
        };

        vm.save = function () {
            vm.isSaving = true;
            if (vm.com_dataunacquiring.id !== null) {
                Com_dataunacquiring.update(vm.com_dataunacquiring, onSaveSuccess, onSaveError);
            } else {
                Com_dataunacquiring.save(vm.com_dataunacquiring, onSaveSuccess, onSaveError);
            }
        };

        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
    }
})();
