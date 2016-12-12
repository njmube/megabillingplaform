(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Freecom_acquiring_dataDetailController', Freecom_acquiring_dataDetailController);

    Freecom_acquiring_dataDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Freecom_acquiring_data', 'Freecom_public_notaries'];

    function Freecom_acquiring_dataDetailController($scope, $rootScope, $stateParams, entity, Freecom_acquiring_data, Freecom_public_notaries) {
        var vm = this;
        vm.freecom_acquiring_data = entity;
        vm.load = function (id) {
            Freecom_acquiring_data.get({id: id}, function(result) {
                vm.freecom_acquiring_data = result;
            });
        };
        var unsubscribe = $rootScope.$on('megabillingplatformApp:freecom_acquiring_dataUpdate', function(event, result) {
            vm.freecom_acquiring_data = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();
