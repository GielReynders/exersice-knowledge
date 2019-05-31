package contracts
import org.springframework.cloud.contract.spec.Contract
Contract.make {
    request {
        method PUT()
        url '/persons'
        body([
                "id": "1",
                "firstName": "Giel",
                "lastName": "Reynders",
                "age": "27"
        ])
        headers {
            contentType applicationJson()
        }
    }
    response {
        status CREATED()
    }
}
