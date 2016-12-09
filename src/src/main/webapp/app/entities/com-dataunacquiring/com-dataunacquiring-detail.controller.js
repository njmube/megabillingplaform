(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Com_dataunacquiringDetailController', Com_dataunacquiringDetailController);

    Com_dataunacquiringDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Com_dataunacquiring', 'Com_acquiring_data'];

    function Com_dataunacquiringDetailController($scope, $rootScope, $stateParams, entity, Com_dataunacquiring, Com_acquiring_data) {
        var vm = this;
        vm.com_dataunacquiring = entity;
        vm.load = function (id) {
            Com_dataunacquiring.get({id: id}, function(result) {
                vm.com_dataunacquiring = result;
            });
        };
        var unsubscribe = $rootScope.$on('megabillingplatformApp:com_dataunacquiringUpdate', function(event, result) {
            vm.com_dataunacquiring = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();
