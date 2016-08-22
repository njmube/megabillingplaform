(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Freecom_custom_unitDetailController', Freecom_custom_unitDetailController);

    Freecom_custom_unitDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Freecom_custom_unit'];

    function Freecom_custom_unitDetailController($scope, $rootScope, $stateParams, entity, Freecom_custom_unit) {
        var vm = this;

        vm.freecom_custom_unit = entity;

        var unsubscribe = $rootScope.$on('megabillingplatformApp:freecom_custom_unitUpdate', function(event, result) {
            vm.freecom_custom_unit = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
