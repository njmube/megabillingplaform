(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Freecom_dataunacquiringDetailController', Freecom_dataunacquiringDetailController);

    Freecom_dataunacquiringDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Freecom_dataunacquiring', 'Freecom_acquiring_data'];

    function Freecom_dataunacquiringDetailController($scope, $rootScope, $stateParams, entity, Freecom_dataunacquiring, Freecom_acquiring_data) {
        var vm = this;
        vm.freecom_dataunacquiring = entity;
        vm.load = function (id) {
            Freecom_dataunacquiring.get({id: id}, function(result) {
                vm.freecom_dataunacquiring = result;
            });
        };
        var unsubscribe = $rootScope.$on('megabillingplatformApp:freecom_dataunacquiringUpdate', function(event, result) {
            vm.freecom_dataunacquiring = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();
